import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import React from 'react';

const Footer: React.FC = () => {
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      links={[
        {
          key: 'LongWeixu',
          title: '龙威旭',
          href: '',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/longweixu520',
          blankTarget: true,
        },
        {
          key: 'LuoJiayun',
          title: '罗佳韵',
          href: '',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
